# UTF8 and Spray

This application is used to test how to manage encodings with Spray. Right now, the problem is that an UTF-8 value is
interpreted as an ISO-8859-1 string, so the non-ASCII chars are broken.

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

Type

```bash
$ curl -d 'content=¿Más?' localhost:8080
```

in your shell, and the `Ok` will be echoed.

## Check the results

After that, the `/tmp/spray.test` will contain two lines: one with the parameter `content` interpreted as a UTF-8, and another one with the parameter interpreted as ISO-8859-1

Every time you send a `POST`, two new lines will be added to the file.

# Results

Since the content is send as an UTF-8 value, the correct line should be the first one. However, we see that the correct is the second one. Is this correct?

```bash
$ cat /tmp/spray.test 
With UTF-8 = Â¿MÃ¡s?
With ISO-8859 = ¿Más?
```
