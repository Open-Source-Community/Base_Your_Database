import express from 'express';
import mongoose from 'mongoose';
import { User } from '../models/users';

mongoose.connect('mongodb+srv://omniiaibrahim2_db_user:Bhm0ajrPQiIQrzhg@cluster0.p7oey5v.mongodb.net/?appName=Cluster0')
  .then(() => console.log('Connected to MongoDB'))
  .catch(err => console.error('Could not connect to MongoDB', err));

const app = express();
const port = 3000;

app.use(express.json());


app.get('/', (req, res) => {
  res.send('Hello World!');
});

// Create User 
app.post("/users", async (req, res) => {
  const user = await User.create(req.body);
  res.json(user);
});

// Get user by name
app.get("/userbyID/:name", async (req, res) => {
  const user = await User.findOne({ name: req.params.name});
  res.json(user);
});


app.listen(port, () => {
  console.log(`Server is running at http://localhost:${port}`);
});