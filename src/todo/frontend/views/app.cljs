(ns todo.frontend.views.app
  (:require [todo.frontend.state :refer [app-state]]
            [todo.frontend.views.filters :as filters]
            [todo.frontend.views.todos :as todos]))

(defn app []
  [:div.todo-app
   [:h1 "Todo App"]
   (when-let [error (:error @app-state)]
     [:p.error error])
   [todos/todo-form]
   [filters/status-filters]
   [todos/todo-list]])
