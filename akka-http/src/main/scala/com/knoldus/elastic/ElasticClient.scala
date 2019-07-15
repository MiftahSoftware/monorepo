package com.knoldus.elastic

import com.knoldus.utils.Configuration._
import org.apache.http.HttpHost
import org.elasticsearch.client.{RestClient, RestHighLevelClient}

object ElasticClient {
  val client = new RestHighLevelClient(
    RestClient.builder(new HttpHost(host, port, "http"))
  )
}
