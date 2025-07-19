db = db.getSiblingDB('msvc-blog');
db.createCollection('blogs');
db.createCollection('blog_categories');

db = db.getSiblingDB('msvc-coupon');
db.createCollection('coupons');

db = db.getSiblingDB('msvc-enquire');
db.createCollection('enquiries');

db = db.getSiblingDB('msvc-products');
db.createCollection('products');
db.createCollection('brands');
db.createCollection('colors');
db.createCollection('prod_categories');

db = db.getSiblingDB('msvc-ratings');
db.createCollection('ratings');