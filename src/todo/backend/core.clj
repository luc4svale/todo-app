(ns todo.backend.core
  (:require [ring.adapter.jetty :as jetty]
            [todo.backend.db.schema :as schema]
            [todo.backend.http.app :as app])
  (:gen-class))

(defn start-server [port]
  (println (str "Servidor iniciado na porta " port))
  (jetty/run-jetty #'app/app {:port port :join? false}))

(defn -main [& args]
  (let [port (Integer/parseInt (or (first args) "3000"))]
    (schema/migrate!)
    (start-server port)))
