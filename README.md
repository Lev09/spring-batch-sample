# spring-batch-sample

To run and test this app you have to configure the PostgreSQL db **url** in *applications.properties* file.
Also you will need the **sql** script to init DB table.


```

CREATE TABLE people (
    id bigint NOT NULL,
    first_name character varying(20),
    last_name character varying(20)
);

```
