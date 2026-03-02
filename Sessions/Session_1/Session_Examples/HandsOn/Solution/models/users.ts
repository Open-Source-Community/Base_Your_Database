import mongoose, { Schema, Document } from 'mongoose';

const schema = new Schema({
    name: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
});


export const User = mongoose.model('User', schema);