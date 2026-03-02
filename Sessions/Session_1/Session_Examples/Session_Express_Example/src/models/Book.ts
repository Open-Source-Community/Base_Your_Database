import mongoose from "mongoose";
 

const schema  = mongoose.Schema

const bookSchema = new schema({
    bookName: String,
    Author: String,
    publishingDate: String,
    category: String,
    numberOfPages: Number,
    Rates:
    [
        {
            userName: String,
            Rate: 
            {
                type: Number,
                min: 1,
                max: 5,
                requierd: true
            }
        }
    ]
})

const Book = mongoose.model("Book",bookSchema)

module.exports = Book