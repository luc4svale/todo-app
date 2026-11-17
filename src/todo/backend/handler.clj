(ns todo.backend.handler
  (:require [todo.backend.db :as db]
            [clojure.string :as str]))

(defn hello-handler [_request]
  {:status 200
   :body "Hello, World!"})

(defn list-todos-handler [_request]
  {:status 200
   :body {:todos (db/get-all-todos)}})

(defn create-todo-handler [request]
  (let [todo-data (:body request)
        title (:title todo-data)]
    (if (and title (not (str/blank? title)))
      (let [new-todo (db/create-todo todo-data)]
        {:status 201
         :body new-todo})
      {:status 400
       :body {:error "O 'título' (title) é obrigatório"}})))
