# Preferred startup

From the repository root:

`docker compose up --build -d`

This now starts database, backend, and frontend together.

Frontend: `http://localhost`
Backend API: `http://localhost:8080`

# Optional local frontend dev

If you want to run the React dev server instead of the Dockerized frontend:

1. Start backend + database from the repository root:
   `docker compose up --build -d db api`
2. In `frontend`, run:
   `npm install`
3. Start the dev server:
   `npm start`

The frontend now falls back to `http://localhost:8080` automatically, so a `.env` file is optional.

