(ns todo.backend.http.handlers.todos
  (:require [clojure.string :as str]
            [todo.backend.db.todos :as todos]
            [todo.backend.http.request :as req]
            [todo.backend.http.response :as resp]))

(def ^:private not-found-message "Todo não encontrado")

(defn list-todos [_request]
  (resp/ok {:todos (todos/find-all)}))

(defn create-todo [{:keys [body]}]
  (let [title (some-> (:title body) str/trim)]
    (if (str/blank? title)
      (resp/bad-request "O 'título' (title) é obrigatório")
      (resp/created (todos/create! {:title title
                                    :description (:description body)})))))

(defn toggle-todo [request]
  (if-let [todo (some-> (req/path-id request) todos/toggle!)]
    (resp/ok todo)
    (resp/not-found not-found-message)))

(defn delete-todo [request]
  (if-let [todo (some-> (req/path-id request) todos/delete!)]
    (resp/ok todo)
    (resp/not-found not-found-message)))
