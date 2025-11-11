wrk.method = "POST"
wrk.headers["Content-Type"] = "application/json"
wrk.body = [[{"accountId":1,"amount":50.00,"currency":"EUR","direction":"IN","description":"wrk load"}]]
