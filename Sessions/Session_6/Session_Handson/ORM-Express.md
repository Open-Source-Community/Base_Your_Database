# ORM Session — Prisma with Express

## is a next-generation ORM for Node.js and TypeScript that simplifies database access with a type-safe query builder, auto-generated client, and built-in migration tooling.

## 1. Install Prisma CLI & client

Install Prisma CLI as a dev dependency, along with the Prisma Client runtime:

```bash
npm install prisma@5 --save-dev    // 'prisma' CLI tool
npm install @prisma/client@5      // '@prisma/client' Runtime client used in your application
```
Note: Prisma Version Differences:<br>
Prisma v5 (Stable):<br>
Database connection URL and configuration are defined inside the schema.prisma file using the datasource block.<br>
Prisma v7 (New):<br>
Database URL and some configuration are moved to a separate prisma.config.ts file, instead of being fully managed inside schema.prisma.<br>
---


## 2. Initialize Prisma

the init command will create and initialize prisma directory with a schema.prisma configuraion file.
```bash
npx prisma init
```
the folders structure now become:
```
your-project/
├── prisma/
│   └── schema.prisma   <- your data models live here
└── .env                
```

---
## 3. Configure the Database

set your `DATABASE_URL="postgresql://[username]:[password]@[host]:[port]/[database]"` in the .env file.

Then update the `datasource` block in `prisma/schema.prisma`:

```prisma
generator client {
  provider = "prisma-client-js"
}

datasource db {
  provider = "sqlite"
  url      = env("DATABASE_URL")
}


```

---

## 4. Define Your Schema

Update the `prisma/schema.prisma` file to define your data models with the required fields:

```prisma
model User {
  id       Int     @id @default(autoincrement())
  email    String  @unique
  password String
  age      Int?
}
```

---

## 5. Run Migrations

after Creating your database tables based on your schema then generate the Prisma Client & add new migration:

```bash
npx prisma migrate dev --name migrateName
npx prisma generate
```

After running these commands, Prisma will create a migration file named "migrateName" if changes to the schema are detected and automatically apply those changes to the database. Additionally, the Prisma Client will be regenerated to reflect any modifications to the schema.

the folders structure now become:
```
prisma
├── migrations
│   └── 20201207100915_init
│       └── migration.sql
└── schema.prisma
```
---

## 6. Use Prisma in Express

Instantiate the Prisma Client in your Express app to interact with the database:

```js
const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();
const express = require('express');
const app = express();

app.get('/users', async (req, res) => {
  const users = await prisma.user.findMany();
  res.json(users);
});
```

---

## 7. Prisma Studio (Visual Editor)

This is a GUI for your database. Run:

```bash
npx prisma studio
```

You can:
- View tables
- Add rows
- Edit users
- Delete data