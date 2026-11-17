(ns todo.backend.db.core
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(def db-spec {:dbtype "sqlite"
              :dbname "prod.db"})

(def ds (jdbc/get-datasource db-spec))

(def opts {:builder-fn rs/as-unqualified-kebab-maps})

(defn execute! [sql-params]
  (jdbc/execute! ds sql-params opts))

(defn execute-one! [sql-params]
  (jdbc/execute-one! ds sql-params opts))
