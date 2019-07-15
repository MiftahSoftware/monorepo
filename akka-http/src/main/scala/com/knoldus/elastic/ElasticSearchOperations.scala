package com.knoldus.elastic

import com.knoldus.model.EmployeeAddress
import com.knoldus.utils.Configuration.{empAddressIndex, empAddressType}
import org.elasticsearch.action.index.{IndexRequest, IndexResponse}
import org.elasticsearch.action.search.{SearchRequest, SearchResponse}
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import play.api.libs.json.{Format, Json}

trait ElasticSearchOperations {

  val client: RestHighLevelClient = ElasticClient.client
  implicit val format: Format[EmployeeAddress] = Json.format[EmployeeAddress]

  def insertEmployeeAddress(employeeAddress: EmployeeAddress): IndexResponse = {
    val indexRequest = new IndexRequest(empAddressIndex, empAddressType, employeeAddress.id)
    val jsonString = Json.stringify(Json.toJson(employeeAddress))
    indexRequest.source(jsonString, XContentType.JSON)
    client.index(indexRequest)
  }


  def searchEmployeeAddress(fieldName: String, fieldValue: String): SearchResponse = {
    val searchRequest = new SearchRequest(empAddressIndex)
    val searchSourceBuilder = new SearchSourceBuilder
    searchSourceBuilder.query(QueryBuilders.matchPhraseQuery(fieldName, fieldValue))
    searchRequest.source(searchSourceBuilder)
    client.search(searchRequest)
  }

}

object ElasticSearchOperations extends ElasticSearchOperations
