Feature: Test Automation Rest Api

  @api
  Scenario: Test get list user
    Given prepare url valid for "GET_LIST_USERS"
    And hit api get list users
    Then validation status code is equals 200
    Then validation response body get list users
    Then validation response json with JSONSchema "get_list_users.json"

  @api
  Scenario: Test get user by id
    Given prepare url valid for get user by id "60d0fe4f5311236168a109ce"
    When hit api get user
    Then validation status code is equals 200
    And validation response body get user by id
    And validation response json with JSONSchema "get_user_by_id.json"

  @api
  Scenario: Test create new user
    Given prepare valid request payload for new user
    And prepare url valid for "CREATE_NEW_USER"
    When hit api post create new user
    Then validation status code is equals 200
    And validation response body create new user
    And validation response json with JSONSchema "create_users_normal.json"

  @api
  Scenario: Test delete user normal
    Given prepare url valid for get user by id "60d0fe4f5311236168a109cf"
    When hit api get user
    And validation response body get user by id
    Given prepare url valid for "DELETE_USER"
    And hit api delete user
    Then validation status code is equals 400