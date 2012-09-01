# UTF8 and Spray

This application is used to test how to manage encodings with Spray. Right now, the problem is that an UTF-8 value is
interpreted as an ISO-8859-1 string, so the non-ASCII chars are broken.

# The route

The route I want to test (and the most interesting part of this project) is

```scala
post {
  formFields('content) { (content) =>

    resource.write("With UTF-8 = ")
    resource.write(content)
    resource.write("\n")

    resource.write("With ISO-8859 = ")
    resource.write(content)(Codec.ISO8859)
    resource.write("\n")

    _.complete("Ok")
  }
}
```

The full source is in [PagesService.scala](https://github.com/ayosec/spray-enc/blob/master/src/main/scala/com/ayosec/sprayencs/PagesService.scala)

# Test it

## Install and run

Ensure that you have a working [SBT installation](https://github.com/harrah/xsbt/wiki/Getting-Started-Setup) in your machine. Then, download the code and run `sbt run`.

```bash
$ git clone https://github.com/ayosec/spray-enc.git
$ cd spray-enc
$ sbt run
```

After download the dependencies, and compile the project code, the server will be running on http:://localhost:8080/

## Test with a browser

Open the [test.html](https://github.com/ayosec/spray-enc/blob/master/test.html) in your browser, fill the form (with just one field) and send it. An `Ok` should be shown in the browser.

## Test with `curl`

With

```bash
$ curl -d 'content=¿Más?' localhost:8080
```

in your shell, and the `Ok` will be echoed.

## Check the results

After that, the `/tmp/spray.test` will contain two lines: one with the parameter `content` interpreted as a UTF-8, and another one with the parameter interpreted as ISO-8859-1

Every time you send a `POST`, two new lines will be added to the file.

# Results

I will send the string `¿Más? ✓`, which has some characters available on ISO-8559-1 (the `¿` and the `á`) and one character that only exists in Unicode (the `✓`)

## With curl

The request is generated with

```
curl -d 'content=¿Más? ✓' localhost:8080
```

The captured data:

```
POST / HTTP/1.1
User-Agent: curl/7.27.0
Host: localhost:8080
Accept: */*
Content-Length: 19
Content-Type: application/x-www-form-urlencoded

content=..M..s? ..
```

The dots in this output are non-ASCII characters. The two dots after `content=` means that curl sent the `¿` character in raw.

The `tmp/spray.test` file has

```
With UTF-8 = Â¿MÃ¡s? â\x9c\x93
With ISO-8859 = ¿Más? ✓
```

(The `\x9c\x93` is a representation of the two last bytes)

As we can see, the UTF-8 is converted two times.

# With Firefox 16

Using the `test.html`, we send the same string with a regular HTML form.

The captured data:

```
POST / HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:16.0) Gecko/16.0 Firefox/16.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.500
Accept-Encoding: gzip, deflate
Connection: keep-alive
Referer: http://localhost:10000/test.html
Content-Type: application/x-www-form-urlencoded
Content-Length: 34

content=%BFM%E1s%3F+%26%2310003%3B
```

The `tmp/spray.test` file has

```
With UTF-8 = ¿Más? &#10003;
With ISO-8859 = �M�s? &#10003;
```

# With Firefox 14

The captured data:

```
POST / HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:14.0) Gecko/20100101 Firefox/14.0.1 Iceweasel/14.0.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: es-es,es;q=0.8,en-us;q=0.5,en;q=0.3
Accept-Encoding: gzip, deflate
DNT: 1
Connection: keep-alive
Referer: http://localhost:10000/test.html
Cache-Control: max-age=0
Content-Type: application/x-www-form-urlencoded
Content-Length: 35

content=%C2%BFM%C3%A1s%3F+%E2%9C%93
```

The `tmp/spray.test` file has

```
With UTF-8 = Â¿MÃ¡s? â
With ISO-8859 = ¿Más? ✓
```

# With Chrome 20

The captured data:

```
POST / HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 34
Cache-Control: max-age=0
Origin: http://localhost:10000
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11
Content-Type: application/x-www-form-urlencoded
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Referer: http://localhost:10000/test.html
Accept-Encoding: gzip,deflate,sdch
Accept-Language: en-US,en;q=0.8
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3

content=%BFM%E1s%3F+%26%2310003%3B
```

The `tmp/spray.test` file has

```
With UTF-8 = ¿Más? &#10003;
With ISO-8859 = �M�s? &#10003;
```

# With a Ruby client

The Ruby program

```ruby
# coding: utf-8
require "net/http"
puts Net::HTTP.post_form(URI("http://localhost:8080"), content: "¿Más? ✓").body
```

The captured data:

```
POST / HTTP/1.1
Accept: */*
User-Agent: Ruby
Content-Type: application/x-www-form-urlencoded
Host: localhost:8080
Content-Length: 35

content=%C2%BFM%C3%A1s%3F+%E2%9C%93
```

The `tmp/spray.test` file has

```
With UTF-8 = Â¿MÃ¡s? â\x9c\x93
With ISO-8859 = ¿Más? ✓
```

(The `\x9c\x93` is a representation of the two last bytes)
