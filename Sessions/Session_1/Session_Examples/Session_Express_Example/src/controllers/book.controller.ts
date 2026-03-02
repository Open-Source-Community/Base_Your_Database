import { Request, Response } from "express"
const Book = require("./models/Book")

// GET all books
export const getAllBooks = async (req: Request, res: Response) => {
    try {
        const books = await Book.find()

        if (books.length === 0)
            return res.status(404).json({
                message: "There are no books!"
            })

        return res.status(200).json({
            message: "All books",
            books
        })
    } catch (error) {
        return res.status(500).json({
            message: "Something went wrong",
            error
        })
    }
}

// GET book by id
export const getBookById = async (req: Request, res: Response) => {
    try {
        const id = req.params.bookID
        const book = await Book.findById(id)

        if (!book)
            return res.status(404).json({
                message: "Book not found"
            })

        const totalRatings = book.Rates.length
        let sum = 0

        book.Rates.forEach((r: any) => {
            sum += r.Rate
        })

        const averageRate =
            totalRatings > 0 ? (sum / totalRatings).toFixed(1) : 0

        return res.status(200).json({
            message: "Query successful",
            book,
            averageRate
        })

    } catch (error) {
        return res.status(500).json({
            message: "Something went wrong",
            error
        })
    }
}

// CREATE book
export const createBook = async (req: Request, res: Response) => {

    const { name, author, publishDate, category, numberofpages, rates } = req.body

    if (!name || !author || !publishDate || !category || !numberofpages || !rates) {
        return res.status(400).json({
            message: "Missing required fields"
        })
    }

    try {
        const newBook = new Book({
            bookName: name,
            Author: author,
            publishDate,
            category,
            numberOfPages: numberofpages,
            Rates: rates
        })

        await newBook.save()

        return res.status(201).json({
            message: "Book created",
            book: newBook
        })

    } catch (error) {
        return res.status(400).json({
            message: "Error creating book",
            error
        })
    }
}

// UPDATE book
export const updateBook = async (req: Request, res: Response) => {
    try {
        const id = req.params.bookID

        const updatedBook = await Book.findByIdAndUpdate(
            id,
            req.body,
            { new: true, runValidators: true }
        )

        if (!updatedBook)
            return res.status(404).json({
                message: "Book not found"
            })

        return res.status(200).json({
            message: "Book updated",
            book: updatedBook
        })

    } catch (error) {
        return res.status(400).json({
            message: "Error updating book",
            error
        })
    }
}

// DELETE book
export const deleteBook = async (req: Request, res: Response) => {
    try {
        const id = req.params.bookID

        const deletedBook = await Book.findByIdAndDelete(id)

        if (!deletedBook)
            return res.status(404).json({
                message: "Book not found"
            })

        return res.status(200).json({
            message: "Book deleted",
            book: deletedBook
        })

    } catch (error) {
        return res.status(400).json({
            message: "Error deleting book",
            error
        })
    }
}
