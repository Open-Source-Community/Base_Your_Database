## Task: Complete on the Hands on the Remaining CRUD Operations

### 1. Create a new GET endpoint to retrieve all users.

- The endpoint should return all users stored in the database.
- Test it and confirm that all previously inserted users are returned.

---

### 2. Create a PUT endpoint to update a user by their ID.

- The endpoint should:
  - Accept the user ID as a route parameter.
  - Accept updated data (name, email, or password) from the request body.
- Update the user in the database.
- Return the updated user as a response.

#### Test the endpoint:
- Use a valid user ID.
- Change at least one field.
- Confirm in MongoDB that the data was updated.

---

### 3. Create a DELETE endpoint to remove a user by their ID.

- The endpoint should:
  - Accept the user ID as a route parameter.
  - Delete the corresponding user from the database.
- Return a success message after deletion.

#### Test the endpoint:
- Use a valid user ID.
- Confirm the user no longer exists in the database.
- Try retrieving the deleted user and verify it returns a 404 response.

---

### 4. Add Proper Error Handling

For all endpoints:

- If the ID is invalid → return status `400`
- If the user is not found → return status `404`
- If a server error occurs → return status `500`

---

### 5. Final Verification

After completing all endpoints, your API should support:

- Create user  
- Get user by ID  
- Get all users  
- Update user  
- Delete user  

Your application must run without crashes and handle errors properly.
