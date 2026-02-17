import express from "express"
import mongoose from "mongoose"
import dotenv from "dotenv"
import bookRoutes from "./routes/book.routes"

dotenv.config()

const app = express()

app.use(express.json())

// MongoDB connection
mongoose.connect(process.env.MONGO_URI as string)
.then(() => {
    console.log("Connected to MongoDB")
})
.catch((error) => {
    console.log("MongoDB connection error:", error)
})

// Routes
app.use("/books", bookRoutes)

const port = process.env.PORT || 3000

app.listen(port, () => {
    console.log(`Server running on port ${port}`)
})
