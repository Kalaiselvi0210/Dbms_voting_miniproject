# Voting Database Management System (Java Swing + JDBC + MySQL)

This project is a beginner-friendly **Voting DBMS** built with:
- Java Swing (GUI)
- JDBC (backend/database operations)
- MySQL (database)

## Features

- Admin login
- Add/Delete candidates
- View voters
- View results
- Voter registration
- Voter login
- View candidates by booth
- Vote only once (prevents multiple voting)

## Project Files

- `schema.sql` → SQL to create database and tables
- `javaapplication6/DBConnection.java` → DB connection class
- `javaapplication6/*DAO.java` → DAO classes for DB operations
- `javaapplication6/*Page.java` → Swing UI pages
- `javaapplication6/VotingManagementSwing.java` → Main class

## MySQL Credentials Used

- Username: `root`
- Password: `Kalai123!`
- Database: `voting_dbms`

You can change these in `javaapplication6/DBConnection.java`.

## Step 1: Create Database Schema

Open MySQL:

```bash
/usr/local/mysql/bin/mysql -u root -p
```

Then run:

```sql
SOURCE /Users/kalai/Desktop/voting_dbms/schema.sql;
```

## Step 2: Compile Java Files

```bash
javac -cp ".:/Users/kalai/Desktop/voting_dbms/mysql-connector-j-8.4.0.jar" /Users/kalai/Desktop/voting_dbms/javaapplication6/*.java
```

## Step 3: Run Application

```bash
java -cp ".:/Users/kalai/Desktop/voting_dbms/mysql-connector-j-8.4.0.jar:/Users/kalai/Desktop/voting_dbms" javaapplication6.VotingManagementSwing
```

## Default Admin Login

- Username: `admin`
- Password: `admin123`

## Notes

- All SQL queries use `PreparedStatement`.
- Voting logic is transactional:
  - checks `has_voted`
  - increments vote in `result`
  - marks voter as voted
- Voter can vote only in their assigned booth.

---

## Complete Step-by-Step: Access All Features

### A) One-time setup (must do first)

1. Open MySQL:
   ```bash
   /usr/local/mysql/bin/mysql -u root -p
   ```
2. Enter password: `Kalai123!`
3. Create tables and seed data:
   ```sql
   SOURCE /Users/kalai/Desktop/voting_dbms/schema.sql;
   ```
4. Compile project:
   ```bash
   javac -cp ".:/Users/kalai/Desktop/voting_dbms/mysql-connector-j-8.4.0.jar" /Users/kalai/Desktop/voting_dbms/javaapplication6/*.java
   ```
5. Run app:
   ```bash
   java -cp ".:/Users/kalai/Desktop/voting_dbms/mysql-connector-j-8.4.0.jar:/Users/kalai/Desktop/voting_dbms" javaapplication6.VotingManagementSwing
   ```

---

### B) Login page usage

When app opens, you will see 2 panels:
- **Admin Login** (left)
- **Voter Login** (right)

Use the correct panel based on your role.

---

### C) Admin features (full flow)

1. In **Admin Login** enter:
   - Username: `admin`
   - Password: `admin123`
2. Click **Login as Admin**.
3. Admin Dashboard opens with buttons:
   - **Candidate Management**
   - **View Voters**
   - **View Results**
   - **Logout**

#### 1) Candidate Management (Add/Delete Candidates)
1. Click **Candidate Management**.
2. Fill form:
   - Name
   - Party
   - Age (must be 21+)
   - Symbol
   - Booth (dropdown)
3. Click **Add Candidate**.
4. Candidate appears in table.
5. To delete candidate:
   - Select candidate row
   - Click **Delete Selected**

#### 2) View Voters
1. From dashboard click **View Voters**.
2. See complete voter list with:
   - Voter ID, Name, Age, Gender, Address, Username, Has Voted, Booth ID
3. Click **Refresh** anytime.

#### 3) View Results
1. From dashboard click **View Results**.
2. See result table with:
   - Candidate ID, Candidate Name, Party, Booth, Total Votes
3. Click **Refresh Results** to get latest count.

#### 4) Logout
1. Click **Logout** in dashboard.
2. App returns to Login Page.

---

### D) Voter features (full flow)

#### 1) Voter Registration
1. On login page, click **New Voter? Register**.
2. Fill all fields:
   - Name
   - Age (must be 18+)
   - Gender
   - Address
   - Username
   - Password
   - Booth
3. Click **Register**.
4. Success message appears.
5. Click back / return to login page.

#### 2) Voter Login
1. On login page use **Voter Login** panel.
2. Enter the **same username/password created during registration**.
3. Click **Login as Voter**.

#### 3) View Candidates and Vote
1. Voting page opens and shows candidates of voter's booth.
2. Select one candidate row.
3. Click **Vote Selected Candidate**.
4. Confirmation shown: vote successful.

#### 4) Multiple vote prevention (important)
- If same voter tries again, system checks `has_voted`.
- Second vote is blocked with **already voted** message.
- This is enforced in DB transaction (`VoteDAO`).

#### 5) Voter Logout
1. Click **Logout** on voting page.
2. App returns to Login Page.

---

### E) Quick Troubleshooting

1. **Error: Unknown database 'voting_dbms'**
   - Run schema again:
     ```sql
     SOURCE /Users/kalai/Desktop/voting_dbms/schema.sql;
     ```

2. **Invalid admin credentials**
   - Ensure admin record exists in `admin` table (`admin/admin123`).

3. **Voter login fails**
   - Register first, then login with exactly same username/password.

4. **No candidates in voter page**
   - Admin must add candidates for that specific booth.
