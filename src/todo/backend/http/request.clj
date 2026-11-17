(ns todo.backend.http.request)

(defn path-id [request]
  (some-> (get-in request [:path-params :id]) parse-long))
