# 产品和商家信息管理系统 - 实现计划

## [ ] 任务 1：完善 product-server 的产品增删改查接口
- **Priority**: high
- **Depends On**: None
- **Description**:
  - 为 Product 实体添加 merchantId 字段（关联商家）
  - 完善 Product 的增删改查能力
  - 更新数据库表结构
  - 更新所有相关代码（Controller、Service、Mapper、XML）
- **Success Criteria**:
  - Product 具备完整的增删改查功能
  - 可以正常与数据库交互
- **Test Requirements**:
  - `programmatic` TR-1.1: GET /product/{id} 返回正确的产品信息
  - `programmatic` TR-1.2: GET /product/list 返回所有产品列表
  - `programmatic` TR-1.3: POST /product 能成功创建产品
  - `programmatic` TR-1.4: PUT /product 能成功更新产品
  - `programmatic` TR-1.5: DELETE /product/{id} 能成功删除产品
- **Notes**: 遵循现有的代码风格和架构

## [ ] 任务 2：在 user-server 中创建商家信息管理的增删改查接口
- **Priority**: high
- **Depends On**: None
- **Description**:
  - 创建 Merchant 实体
  - 创建 MerchantController、MerchantService、MerchantMapper
  - 创建数据库表
  - 实现完整的增删改查接口
- **Success Criteria**:
  - Merchant 模块具备完整的增删改查功能
  - 可以正常与数据库交互
- **Test Requirements**:
  - `programmatic` TR-2.1: GET /merchant/{id} 返回正确的商家信息
  - `programmatic` TR-2.2: GET /merchant/list 返回所有商家列表
  - `programmatic` TR-2.3: POST /merchant 能成功创建商家
  - `programmatic` TR-2.4: PUT /merchant 能成功更新商家
  - `programmatic` TR-2.5: DELETE /merchant/{id} 能成功删除商家
- **Notes**: 遵循现有的代码风格和架构

## [ ] 任务 3：在 product-server 中提供商品的各种条件查询接口
- **Priority**: high
- **Depends On**: 任务 1, 任务 2
- **Description**:
  - 创建 ProductQuery 查询条件实体
  - 实现多条件查询接口（按商家、价格范围、库存、名称等）
  - 支持分页查询
- **Success Criteria**:
  - 能够按多种条件查询商品
  - 支持分页功能
- **Test Requirements**:
  - `programmatic` TR-3.1: GET /product/query 能按商家ID查询商品
  - `programmatic` TR-3.2: GET /product/query 能按价格范围查询商品
  - `programmatic` TR-3.3: GET /product/query 能按库存查询商品
  - `programmatic` TR-3.4: GET /product/query 能按商品名称模糊查询
  - `programmatic` TR-3.5: GET /product/query 支持分页
- **Notes**: 参考 UserController 的查询设计模式
