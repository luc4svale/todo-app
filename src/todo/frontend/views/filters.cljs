(ns todo.frontend.views.filters
  (:require [todo.frontend.actions :as actions]
            [todo.frontend.state :as state :refer [app-state]]))

(defn status-filters []
  (let [current (:status-filter @app-state)]
    [:div.filters {:role "group" :aria-label "Filtrar por status"}
     (for [{:keys [id label]} state/status-filters]
       ^{:key id}
       [:button.chip {:type "button"
                      :class (when (= id current) "chip--active")
                      :aria-pressed (= id current)
                      :on-click #(actions/set-status-filter! id)}
        label])]))
