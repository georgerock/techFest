(ns techfest.core
	(:require
		[om.core :as om :include-macros true]
		[om.dom :as dom :include-macros true])
	(:require-macros [cljs.core.async.macros :refer [go go-loop]])
	(:import [goog.net XhrIo]
             goog.net.EventType
             [goog.events EventType]
             [goog.structs Map]
             [goog.Uri QueryData]))

(enable-console-print!)

(println "This should work")

(defonce app-state (atom {:busses []
					   	  :stations []))

(extend-type js/NodeList
	ISeqable
	(-seq [array] (array-seq array 0)))

(extend-type js/FileList
	ISeqable
	(-seq [array] (array-seq array 0)))

(extend-type js/HTMLCollection
	ISeqable
	(-seq [array] (array-seq array 0)))

(defrecord Buss [id coords number])
(defrecord Station [id coords])

(defn json-xhr [{:keys [method url data on-complete token]}]
 (let [xhr (XhrIo.)
	   content-type (if (= method "GET")
						"application/json"
						"application/x-www-form-urlencoded-data; charset=UTF8")]
	 (events/listen xhr goog.net.EventType.COMPLETE
		 (fn [e]
			 (on-complete (js->clj (if (or (= method "GET") (= url "/auth"))
									   (.getResponseJson xhr)
									   (.getResponse xhr))))))
	 (if token
		 (. xhr
		 (send url method (when data (-> (clj->js data) (Map.) (QueryData.createFromMap) (.toString)))
			 #js {"Content-Type" content-type "Authorization" token}))
		 (. xhr
		 (send url method (when data (-> (clj->js data) (Map.) (QueryData.createFromMap) (.toString)))
			 #js {"Content-Type" content-type})))))

(defn xhr-request [method url data on-complete token]
(json-xhr
	{:method method
	 :url url
	 :data data
	 :on-complete on-complete
	 :token token}))

(defn app-view [data owner]
	(reify
		om/IRender
		(render [_])))

(om/root application app-state
    {:target (. js/document (getElementById "app"))})
