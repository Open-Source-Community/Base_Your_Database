//download redis
//open redis-cli
//ping --> pong (perfect)

//npm init -y
//npm i express ioredis

const express = require("express");
const app = express();
const Redis = require("ioredis");
const redis = new Redis();

const DB = {
  1: { id: 1, name: "Alice" },
  2: { id: 2, name: "Bob" },
  3: { id: 3, name: "Charlie" },
};

//get(key)
//set(key,value,"EX",TTL)
//1: check cache
//2: retreive from db
//3: set in cache

app.get("/user/:id", async (req, res) => {
  const userId = Number(req.params.id);
  const cacheKey = `user:${userId}`;
  //if found in cache
  const cacheData = await redis.get(cacheKey);
  if (cacheData) return res.json(JSON.parse(cacheData));

  //retreive from db
  const user = DB[userId];
  if (!user) return res.status(404);

  //save in cache
  await redis.set(cacheKey, JSON.stringify(user), "EX", 20);
  return res.json(user);
});

app.listen(3000, () => {
  console.log("server is running");
});
