# Assignment approach outline

1.  Spring boot init with IntelliJ.
2.  Load all the necessary deps.
3.  Setup local and docker environment.
4.  Start with domain models, enums, dtos.
5.  MyBatis mappers and interfaces.
6.  Start with services.
7.  Test some services.
8.  Start with controllers.
9.  Test controllers.
10. Add rabbitmq.
11. Add testing coverage
12. Final testing & polishing.

# Source code

***Clone GitHub repository***
```bash
    git clone https://github.com/andmiron/banking.git
```

# How to build and run

1. Either locally (Linux)

```bash
    git clone https://github.com/andmiron/banking.git
    
    cd banking
    
    cp .env.example .env
    
    ./gradlew build
    
    ./gradlew bootRun
```
Tests will be run.
This will automatically start all the needed containers and run application locally. 
The app is running on port 8080 so it must be free, as well as postgres 5432 and rabbitmq 5672.
Since for the sake of test assignment I did not include migrations, on start, sql script will be executed and db will be populated with tables. (So if you want to restart the app, the db container volume must also be removed.)

2. Or running docker compose file

```bash
    git clone https://github.com/andmiron/banking.git
    
    cd banking
    
    cp .env.example .env

    docker compose up
    # OR
    docker compose up -d
```

App will be listening on port 8081.
Tests will not be executed in docker environment due to complex setup for testcontainers inside docker.


# Explanation of important choices in your solution

Generally the task is a challenge for me because I have a little experience with spring. I would say that the biggest struggle was to make MyBatis work with xml mappers and interfaces. I've chosen xml mappers because I assume it might be used in production as well. Besides that, just tried to follow best practices (want to believe in that). The task did not mention any requirements for security so it was completely omitted.

# Estimate how many transactions

Ran wrk tool to test http load:

```bash
    # Warmup
    wrk -t2 -c16 -d10s -s wrk.lua http://localhost:8080/transactions
    
    #Test
    wrk -t4 -c32 -d30s -s wrk.lua http://localhost:8080/transactions
```

```
Running 30s test @ http://localhost:8080/transactions
  4 threads and 32 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   213.90ms  125.06ms 799.80ms   80.06%
    Req/Sec    31.38     20.70    80.00     61.70%
  520 requests in 30.06s, 130.51KB read
  Socket errors: connect 0, read 0, write 0, timeout 159
Requests/sec:     17.30
Transfer/sec:      4.34KB
```

# Usage of AI
Main tool that was used is CodexCLI

