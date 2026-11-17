(ns todo.backend.db.todos
  (:require [todo.backend.db.core :as db]))

(defn find-all []
  (db/execute! ["SELECT * FROM todos ORDER BY created_at DESC"]))

(defn create! [{:keys [title description]}]
  (db/execute-one! ["
    INSERT INTO todos (title, description, completed, created_at)
    VALUES (?, ?, 0, ?)
    RETURNING *"
    title
    description
    (str (java.time.Instant/now))]))

(defn toggle! [id]
  (db/execute-one! ["
    UPDATE todos
    SET completed = (1 - completed)
    WHERE id = ?
    RETURNING *"
    id]))

(defn delete! [id]
  (db/execute-one! ["DELETE FROM todos WHERE id = ? RETURNING *" id]))
