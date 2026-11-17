(ns todo.frontend.views.todos
  (:require [clojure.string :as str]
            [todo.frontend.actions :as actions]
            [todo.frontend.state :refer [app-state]]))

(defn- submit! [e]
  (.preventDefault e)
  (let [title (str/trim (:input-text @app-state))]
    (when-not (str/blank? title)
      (actions/create-todo! {:title title})
      (swap! app-state assoc :input-text ""))))

(defn todo-form []
  [:form.todo-input {:on-submit submit!}
   [:input {:type "text"
            :placeholder "O que precisa ser feito?"
            :value (:input-text @app-state)
            :on-change #(swap! app-state assoc :input-text (.. % -target -value))}]
   [:button {:type "submit"} "Adicionar"]])

(defn- todo-item [{:keys [id title completed]}]
  [:li.todo-item {:class (when completed "completed")}
   [:input.todo-checkbox {:type "checkbox"
                          :checked (boolean completed)
                          :on-change #(actions/toggle-todo! id)}]
   [:span.todo-title title]
   [:button.delete-btn {:type "button"
                        :on-click #(actions/delete-todo! id)}
    "X"]])

(defn todo-list []
  [:ul.todo-list
   (for [todo (:todos @app-state)]
     ^{:key (:id todo)}
     [todo-item todo])])
