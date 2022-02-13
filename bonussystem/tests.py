import requests
import json
import random

def testEndpoint(url, method, expectedCode, json=None, data=None):
	r = None
	if method == "GET":
		r = requests.get(url)
		
	elif method == "POST":
		r = requests.post(url, json=json, data=data)

	assert r.status_code == expectedCode

	print(f"{method}: {url} test completed. Response: {r.content}")
	return r.content

apiRoot = "http://127.0.0.1:8080/api"

user = json.loads(testEndpoint(apiRoot + "/create/client", "POST", 200, json={"name":"Rob", "isActive:": "True"}))
card =  json.loads(testEndpoint(apiRoot + "/create/card", "POST", 200, 
	data={"balance":0, "number": random.randint(111111, 999999), "clientId:": user["id"]}))

bonus = json.loads(testEndpoint(apiRoot + "/create/bonus", "POST", 200, data={"card_id": card["id"], "value": 150}))

testEndpoint(apiRoot + f'/create/bonus?currency={card["id"]}&client_id={user["id"]}', "GET", 200)
testEndpoint(apiRoot + f'/balance/details?period=2022-02&currency={card["id"]}&client_id={user["id"]}', "GET", 200)

testEndpoint(apiRoot + "/block/card", "POST", 200, data={"card_id": card["id"]})
testEndpoint(apiRoot + "/block/client", "POST", 200, data={"clientId": user["id"]})

testEndpoint(apiRoot + "/delete/bonus", "POST", 200, data={"bonusId": bonus["id"]})
testEndpoint(apiRoot + f'/create/bonus?currency={card["id"]}&client_id={user["id"]}', "GET", 200)

testEndpoint(apiRoot + "/delete/card", "POST", 200, data={"cardId": card["id"]})
testEndpoint(apiRoot + "/delete/client", "POST", 200, data={"clientId": user["id"]})

