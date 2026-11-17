(ns todo.frontend.views.modal
  (:require [todo.frontend.actions :as actions]
            [todo.frontend.state :refer [app-state]]))

(defn confirm-dialog []
  (when-let [{:keys [title message confirm-label on-confirm]} (:confirm @app-state)]
    [:div.modal-backdrop {:on-click actions/close-confirmation!
                          :on-key-down #(when (= "Escape" (.-key %))
                                          (actions/close-confirmation!))}
     [:div.modal {:role "alertdialog"
                  :aria-modal true
                  :aria-labelledby "modal-title"
                  :on-click #(.stopPropagation %)}
      [:h2#modal-title title]
      [:p message]
      [:div.modal__actions
       [:button.btn {:type "button"
                     :auto-focus true
                     :on-click actions/close-confirmation!}
        "Cancelar"]
       [:button.btn.btn--danger {:type "button"
                                 :on-click (fn []
                                             (on-confirm)
                                             (actions/close-confirmation!))}
        confirm-label]]]]))
