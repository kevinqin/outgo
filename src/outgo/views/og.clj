(ns outgo.views.og
  (:use [hiccup.core :only (h html)]
        [hiccup.form :only (form-to label text-field hidden-field text-area submit-button)])
  (:require [outgo.views.layout :as layout]))

(defn get-value [username today-outgo field type]
  (if (and (= type :int) (empty? today-outgo))
   0
    (if (= username "jessica") (field (first today-outgo)) (field (second today-outgo)))))

(defn nested-form [usernames today-outgo]
  (let [fields [:breakfast :lunch :dinner :drink :individual :common]]
    (for [field fields]
      (html (label {:class "column label-part"} field field)
            (for [username usernames]
              (text-field {:class "column one.column"} (str username "[" (name field) "]") (get-value username today-outgo field :int)))
            [:div {:class "clear"}]
            (label {:class "column label-part"} "Note" "Note")
            (for [username usernames]
              (text-area {:class "column one.column"} (str username "[" (name field) "_note" "]") (get-value username today-outgo (keyword (subs (str field "_note") 1)) :nil)))
            [:div {:class "clear"}]))))

(defn og-form [today-outgo]
  [:div {:id "og-form" :class "sixteen columns alpha omega"}
   [:h2
    [:span {:class "kevin"} "Kevin"]
    [:span {:class "jessica"} "Jessica"] ]
   (form-to [:post "/"]
            (nested-form ["kevin", "jessica"] today-outgo)
            (hidden-field "kevin[user_id]" 1)
            (hidden-field "jessica[user_id]" 2)
            (submit-button "Submit!"))])

(defn total [col]
  (reduce + (map col (filter #(not (re-matches #"[a-z]+_note|id|user_id|created_at" (name %))) (keys col)))))

(defn histories-div [models]
  (if (seq models)
    (loop [vec [:ul {:class "histories"}] [kevin jessica :as coll] models]
      (if (not (seq kevin))
        vec
        (recur (conj vec (let [count (+ (total kevin) (total jessica))]
                           [:li (str (subs (str (:created_at kevin)) 0 10) " - " count)]))
               (drop 2 coll))))))

(defn index [today-outgo]
  (layout/common "OUTGO"
                 (og-form today-outgo)))

(defn histories [models]
  (layout/common "OUTGO HISTORIES"
                 (histories-div models)))
