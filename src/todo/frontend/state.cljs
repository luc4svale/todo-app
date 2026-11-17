(ns todo.frontend.state
  (:require [reagent.core :as r]))

(defonce app-state
  (r/atom {:todos []
           :input-text ""
           :status-filter :all
           :error nil}))

(def status-filters
  [{:id :all :label "Todas"}
   {:id :pending :label "Pendentes"}
   {:id :done :label "Concluídas"}])

(defn- matches-status? [status todo]
  (case status
    :pending (not (:completed todo))
    :done (boolean (:completed todo))
    true))

(defn visible-todos [{:keys [todos status-filter]}]
  (filter #(matches-status? status-filter %) todos))
