(ns todo.frontend.views.todos
  (:require [clojure.string :as str]
            [todo.frontend.actions :as actions]
            [todo.frontend.state :as state :refer [app-state]]))

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

(defn- confirm-delete! [{:keys [id title]}]
  (actions/ask-confirmation!
   {:title "Excluir tarefa?"
    :message (str "“" title "” será removida. Essa ação não pode ser desfeita.")
    :confirm-label "Excluir"
    :on-confirm #(actions/delete-todo! id)}))

(defn- todo-item [{:keys [id title completed] :as todo}]
  [:li.todo-item {:class (when completed "completed")}
   [:input.todo-checkbox {:type "checkbox"
                          :checked (boolean completed)
                          :aria-label (str "Concluir “" title "”")
                          :on-change #(actions/toggle-todo! id)}]
   [:span.todo-title title]
   [:button.delete-btn {:type "button"
                        :aria-label (str "Excluir “" title "”")
                        :on-click #(confirm-delete! todo)}
    "✕"]])

(defn- empty-message [{:keys [todos]}]
  (if (empty? todos)
    "Nenhuma tarefa ainda. Comece adicionando uma acima."
    "Nenhuma tarefa neste filtro."))

(defn todo-list []
  (let [current @app-state
        todos (state/visible-todos current)]
    (if (empty? todos)
      [:p.empty-state (empty-message current)]
      [:ul.todo-list
       (for [todo todos]
         ^{:key (:id todo)}
         [todo-item todo])])))
