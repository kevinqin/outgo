(ns outgo.models.og
  (:require [clojure.java.jdbc :as sql]
            [clj-time.core :refer [today-at now]]
            [clj-time.coerce :refer [to-sql-time]]))

(defn all []
  "Get outgos of recent 30 days"
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from outgos order by id desc limit 60"]
      (into [] results))))

(defn today-outgo []
  "Get outgos of recent 30 days"
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from outgos where created_at > ? order by id desc limit 2" (to-sql-time (today-at 00 00))]
      (into [] results))))

(defn get-user-id-by-name [name]
  "Get outgos of recent 30 days"
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select id from users where nick = ?" name]
      (:id (first results)))))

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
    (let [outgo (convert-params outgo)
          result (sql/with-query-results result
                   ["select * from outgos where user_id = ? and created_at > ? order by id desc limit 1" (:user_id outgo) (to-sql-time (today-at 00 00))]
                   into [] result)]
      (if result
        (sql/update-values :outgos ["id=?" (:id (first result))] outgo)
        (sql/insert-record :outgos outgo)))))
