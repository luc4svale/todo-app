(ns todo.frontend.api)

(def base-url "http://localhost:3000/api")

(defn- request [method path body]
  (-> (js/fetch (str base-url path)
                (clj->js (cond-> {:method method}
                           body (assoc :headers {"Content-Type" "application/json"}
                                       :body (js/JSON.stringify (clj->js body))))))
      (.then (fn [response]
               (.then (.json response)
                      (fn [data]
                        (let [data (js->clj data :keywordize-keys true)]
                          (if (.-ok response)
                            data
                            (throw (js/Error. (or (:error data)
                                                  (str "HTTP " (.-status response)))))))))))))

(defn list-todos []
  (request "GET" "/todos" nil))

(defn create-todo [todo]
  (request "POST" "/todos" todo))

(defn toggle-todo [id]
  (request "POST" (str "/todos/" id "/toggle") nil))

(defn delete-todo [id]
  (request "DELETE" (str "/todos/" id) nil))
