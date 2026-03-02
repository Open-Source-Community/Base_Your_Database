import { Router } from "express"
import {
    getAllBooks,
    getBookById,
    createBook,
    updateBook,
    deleteBook
} from "../controllers/book.controller"

const router = Router()

router.get("/", getAllBooks)
router.get("/:bookID", getBookById)
router.post("/", createBook)
router.put("/:bookID", updateBook)
router.delete("/:bookID", deleteBook)

export default router
