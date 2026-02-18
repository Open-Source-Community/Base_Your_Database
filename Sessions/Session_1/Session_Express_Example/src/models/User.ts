import mongoose from "mongoose";
const Book = require("./Book")

const schema  = mongoose.Schema

const userSchema = new schema({
    username: String,
    email: String,
    password: String,
    readBooks: [{
        type: schema.Types.ObjectId,
        ref: 'Book'
    }],
    booksToRead: [{
        type: schema.Types.ObjectId,
        ref: 'Book'
    }]    
})


const User = mongoose.model("User",userSchema)

module.exports = User