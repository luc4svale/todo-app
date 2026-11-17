(ns todo.backend.db.schema
  (:require [todo.backend.db.core :as db]))

(defn migrate! []
  (db/execute! ["
    CREATE TABLE IF NOT EXISTS todos (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT,
      description TEXT,
      completed BOOLEAN DEFAULT 0,
      created_at TEXT
    )"]))
