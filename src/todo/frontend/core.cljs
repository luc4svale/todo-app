(ns todo.frontend.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]
            [todo.frontend.actions :as actions]
            [todo.frontend.views.app :refer [app]]))

(defonce root (rdom/create-root (js/document.getElementById "app")))

(defn ^:export init []
  (.render root (r/as-element [app]))
  (actions/load-todos!))
