(ns todo.backend.db.core
  (:require [next.jdbc :as jdbc]))

(def db-spec {:dbtype "sqlite"
              :dbname "prod.db"})

(def ds (jdbc/get-datasource db-spec))

(defn execute! [sql-params]
  (jdbc/execute! ds sql-params))

(defn execute-one! [sql-params]
  (jdbc/execute-one! ds sql-params))
