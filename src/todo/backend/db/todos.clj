(ns todo.backend.db.todos
  (:require [todo.backend.db.core :as db]))

(defn- ->todo [row]
  (some-> row (update :completed #(if (boolean? %) % (= 1 %)))))

(defn find-all []
  (mapv ->todo (db/execute! ["SELECT * FROM todos ORDER BY created_at DESC"])))

(defn create! [{:keys [title description]}]
  (->todo (db/execute-one! ["
    INSERT INTO todos (title, description, completed, created_at)
    VALUES (?, ?, 0, ?)
    RETURNING *"
    title
    description
    (str (java.time.Instant/now))])))

(defn toggle! [id]
  (->todo (db/execute-one! ["
    UPDATE todos
    SET completed = (1 - completed)
    WHERE id = ?
    RETURNING *"
    id])))

(defn delete! [id]
  (->todo (db/execute-one! ["DELETE FROM todos WHERE id = ? RETURNING *" id])))
