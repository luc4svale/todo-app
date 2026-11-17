(ns todo.backend.http.response)

(defn ok [body]
  {:status 200 :body body})

(defn created [body]
  {:status 201 :body body})

(defn bad-request [message]
  {:status 400 :body {:error message}})

(defn not-found [message]
  {:status 404 :body {:error message}})

(defn conflict [message]
  {:status 409 :body {:error message}})
