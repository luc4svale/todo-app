(ns todo.frontend.state
  (:require [reagent.core :as r]))

(defonce app-state
  (r/atom {:todos []
           :input-text ""
           :error nil}))
