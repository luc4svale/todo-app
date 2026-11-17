(ns todo.backend.http.app
  (:require [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [todo.backend.http.handlers.todos :as todos]))

(defn- hello [_request]
  {:status 200 :body "Hello, World!"})

(def routes
  ["/api"
   ["/hello" {:get {:handler hello}}]
   ["/todos" {:get  {:handler todos/list-todos}
              :post {:handler todos/create-todo}}]
   ["/todos/:id/toggle" {:post {:handler todos/toggle-todo}}]
   ["/todos/:id" {:delete {:handler todos/delete-todo}}]])

(def app
  (ring/ring-handler
   (ring/router routes)
   (ring/create-default-handler)
   {:middleware [[wrap-cors
                  :access-control-allow-origin [#"http://localhost:8000"]
                  :access-control-allow-methods [:get :post :put :delete]
                  :access-control-allow-headers ["Content-Type"]]
                 wrap-json-response
                 [wrap-json-body {:keywords? true}]
                 wrap-params
                 wrap-keyword-params]}))
