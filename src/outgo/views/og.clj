(ns outgo.views.og
  (:use [hiccup.core :only (h html)]
        [hiccup.form :only (form-to label text-field hidden-field text-area submit-button)])
  (:require [outgo.views.layout :as layout]))

(defn nested-form [username]
  (let [fields [:breakfast :lunch :dinner :drink :individual :common]]
        (for [field fields]
          (str
           (html (label field field))
           (html (text-field (str username "[" (name field) "]") 0))
           (html (label (str (name field) "_note") (str (name field) "_note")))
           (html (text-area (str username "[" (name field) "_note" "]")))))))

(defn og-form []
  [:div {:id "og-form" :class "sixteen columns alpha omega"}
   (form-to [:post "/"]
     (nested-form "kevin")
     (hidden-field "kevin[user_id]" "1")
     (nested-form "jessica")
     (hidden-field "jessica[user_id]" "2")
     (submit-button "Submit!"))])

(defn index [models]
  (layout/common "OUTGO"
                 (og-form)))
(index [])
