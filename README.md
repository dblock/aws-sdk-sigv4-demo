# AWS SDK v2 Sigv4 Request Demo

Sign and make HTTPs requests to AWS services in pure AWS SDK v2.

Loosely inspired by [aws-request-signing-apache-interceptor](https://github.com/acm19/aws-request-signing-apache-interceptor), minus the interceptor.

## Amazon OpenSearch

To run the [OpenSearch sample](src/main/java/org/dblock/AmazonOpenSearchServiceSample.java), replace the values of `host` and `region` in the source and run the following.

```
export AWS_ACCESS_KEY_ID=
export AWS_SECRET_ACCESS_KEY=
export AWS_SESSION_TOKEN=

mvn compile exec:java -Dexec.mainClass="org.dblock.AmazonOpenSearchServiceSample"
```

## Copyright

Copyright AWS SDK v2 SigV4 Demo Contributors. See [NOTICE](NOTICE) for details.

## License

This demo is licensed under the [Apache 2.0 License](LICENSE).
