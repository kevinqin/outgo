(ns outgo.models.og
  (:require [clojure.java.jdbc :as sql]))

(defn all []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from outgos order by id desc"]
      (into [] results))))

(defn- to-int
  "converts a string to an integer"
  [s]
  (Integer/valueOf s))

(def ^:private conversions
  "a map of parameter name and converter"
  {:user_id to-int
   :breakfast to-int
   :lunch to-int
   :dinner to-int
   :drink to-int
   :individual to-int
   :common to-int})

(defn- convert-params
  "converts specific parameters to their proper types"
  [params]
  (reduce (fn [m [k v]]
            (if-let [converter-fn (conversions k)]
              (assoc m k (converter-fn v))
              m))
          params params))



(defn create [outgo]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-record :outgos (convert-params outgo))
    ;; (sql/update-or-insert-values :outgos [:user_id (:user_id outgo)] outgo)
    ))
