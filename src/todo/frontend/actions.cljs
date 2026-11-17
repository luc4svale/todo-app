(ns todo.frontend.actions
  (:require [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [todo.frontend.api :as api]
            [todo.frontend.state :refer [app-state]]))

(defn- fail! [e]
  (swap! app-state assoc :error (.-message (or (ex-cause e) e))))

(defn- call! [promise-fn on-ok]
  (swap! app-state assoc :error nil)
  (go
    (try
      (on-ok (<p! (promise-fn)))
      (catch js/Error e
        (fail! e)))))

(defn- replace-todo [todos todo]
  (mapv #(if (= (:id %) (:id todo)) todo %) todos))

(defn load-todos! []
  (call! api/list-todos
         #(swap! app-state assoc :todos (:todos %))))

(defn create-todo! [todo]
  (call! #(api/create-todo todo)
         #(swap! app-state update :todos (fn [todos] (into [%] todos)))))

(defn toggle-todo! [id]
  (call! #(api/toggle-todo id)
         #(swap! app-state update :todos replace-todo %)))

(defn delete-todo! [id]
  (call! #(api/delete-todo id)
         #(swap! app-state update :todos
                 (fn [todos] (filterv (fn [todo] (not= id (:id todo))) todos)))))

(defn set-status-filter! [status]
  (swap! app-state assoc :status-filter status))
