## Docker ##

```
docker build . -t fridaydummy
docker run -m512M --cpus 2 -it -p 8080:8080 --rm fridaydummy
```
