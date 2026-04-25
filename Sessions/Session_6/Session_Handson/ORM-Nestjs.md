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


## 6. Use Prisma in NestJS

### Step 1: Create a Prisma Service

Create a `prisma.service.ts` file to wrap the Prisma Client and integrate it with NestJS lifecycle:
```js
nest g service prisma
```
```ts
// src/prisma/prisma.service.ts

import { Injectable, OnModuleInit } from '@nestjs/common';
import { PrismaClient } from '@prisma/client';

@Injectable()
export class PrismaService extends PrismaClient implements OnModuleInit {
  async onModuleInit() {
    await this.$connect();
  }
}
```

### Step 2: Create a Prisma Module
```js
nest g module prisma
```
```ts
// src/prisma/prisma.module.ts

import { Module } from '@nestjs/common';
import { PrismaService } from './prisma.service';

@Module({
  providers: [PrismaService],
  exports: [PrismaService],
})
export class PrismaModule {}
```

### Step 3: Create a Users Module

Generate the users module, service, and controller using the NestJS CLI:

```bash
nest generate module users
nest generate service users
nest generate controller users
```

### Step 4: Create the Users Service

```ts
// src/users/users.service.ts

import { Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma/prisma.service';

@Injectable()
export class UsersService {
  constructor(private prisma: PrismaService) {}

  findAll() {
    return this.prisma.user.findMany();
  }

  findOne(id: number) {
    return this.prisma.user.findUnique({
      where: { id },
    });
  }

  create(data: { email: string; password: string; age?: number }) {
    return this.prisma.user.create({ data });
  }

  delete(id: number) {
    return this.prisma.user.delete({
      where: { id },
    });
  }
}
```

### Step 5: Create the Users Controller

```ts
// src/users/users.controller.ts

import { Controller, Get } from '@nestjs/common';
import { UsersService } from './users.service';

@Controller('users')
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @Get()
  findAll() { //  get all users
    return this.usersService.findAll();
  }

  @Get(':id') // get user by id
  findOne(@Param('id') id: string) {
    return this.usersService.findOne(Number(id));
  }

}
```

### Step 6: Register in the Users Module
Make sure PrismaModule is imported in UsersModule

```ts
// src/users/users.module.ts

import { Module } from '@nestjs/common';
import { UsersService } from './users.service';
import { UsersController } from './users.controller';
import { PrismaModule } from '../prisma/prisma.module';

@Module({
  imports: [PrismaModule],
  controllers: [UsersController],
  providers: [UsersService],
})
export class UsersModule {}
```

### Step 7: Register in App Module

```ts
// src/app.module.ts

import { Module } from '@nestjs/common';
import { UsersModule } from './users/users.module';

@Module({
  imports: [UsersModule],
})
export class AppModule {}
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