# Test data builder

A set of libraries for creation and overriding of test data.

Requires JDK 1.7 or higher.

### Latest release
The most recent release is 2.0, released May 2, 2017.

To add a dependency using Maven, use the following for core:
```xml
<dependency>
  <groupId>com.github.adben002.testdatabuilder</groupId>
  <artifactId>core</artifactId>
  <version>2.0</version>
</dependency>
```
Or for entity:
```xml
<dependency>
  <groupId>com.github.adben002.testdatabuilder</groupId>
  <artifactId>entity</artifactId>
  <version>2.0</version>
</dependency>
```

To add a dependency using Gradle for core:

```
dependencies {
  compile 'com.github.adben002.testdatabuilder:core:2.0'
}
```
Or for entity:
```
dependencies {
  compile 'com.github.adben002.testdatabuilder:entity:2.0'
}
```

Core example:

```java
public class CoreTest {
    
    private static final DataBuilder DATA_BUILDER = DataBuilder.a();
    
    @Before
    public void setup() {
      DATA_BUILDER
          .reset()
          .add(core(TestSubBean.class).overrides(ovrrdSet(TestSubBean.class)
              .all(coreFields(TestSubBean.class, new Overrider<TestSubBean>() {
                @Override
                public void setFields(final TestSubBean o) {
                  o.setA(AlphaNumericHelper.toAlphaNumeric(0));
                }
              }))))
          .add(core(TestBean.class, TEST_BEAN_COUNT).overrides(ovrrdSet(TestBean.class)
              .all(coreFields(TestBean.class, new Overrider<TestBean>() {
                @Override
                public void setFields(final TestBean o) {
                  o.setJ1(ALL_CORE_J1);
                }
              }))
              .idx(INDEX, coreFields(TestBean.class, new Overrider<TestBean>() {
                @Override
                public void setFields(final TestBean o) {
                  o.setJ1(OTHER_CORE_J1);
                  o.setSubBean(builtLookup(TestSubBean.class, 0).lookup());
                }
              }))))
          .build();
    }

}

```

Entity example:

Using the entity structure from MySQL Sakila Database (https://dev.mysql.com/doc/sakila/en/sakila-structure.html)

With Actor hibernate class and metamodel class:
```java
@Entity
@Table(name = "actor", indexes = @Index(columnList = "last_name", name = "idx_actor_last_name"))
public class Actor implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "actor_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Short actorId;

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
    private String lastName;

    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    private Timestamp lastUpdate;

    @OneToMany(mappedBy = "actor")
    private List<FilmActor> filmActors = Lists.newArrayList();

    public Actor() {
        this.lastUpdate = new Timestamp(System.currentTimeMillis());
    }
}
```
```java
@StaticMetamodel(Actor.class)
public class Actor_ {
    public static volatile SingularAttribute<Actor, Short> actorId;
    public static volatile SingularAttribute<Actor, String> firstName;
    public static volatile SingularAttribute<Actor, String> lastName;
    public static volatile SingularAttribute<Actor, Timestamp> lastUpdate;
}

```

We can use the databuilder, as such:

```java
public class EntityTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void eg() { 
      this.builder
          .add(entity(TestEntity.class, TEST_ENTITY_COUNT).overrides(ovrrdSet(TestEntity.class)
              .all(entityFields(TestEntity.class, new Overrider<TestEntity>() {
                @Override
                public void setFields(final TestEntity testEntity) {
                  testEntity.setE(OVERRIDE_E);
                }
              }))))
          .add(entity(Actor.class, 3))
          .add(entity(Film.class, 2))
          .build();
    }  

}
```

In this case it generates:

| actor_id | first_name | last_name | last_update         |
|----------|------------|-----------|---------------------|
| 1        | Adam       | A         | 2017-05-05 16:37:44 |
| 2        | Adam       | B         | 2017-05-05 16:37:44 |
| 3        | Adam       | C         | 2017-05-05 16:37:44 |
| 4        | Adam       | D         | 2017-05-05 16:37:44 |
| 5        | Adam       | E         | 2017-05-05 16:37:44 |

And film:

| film_id | description | last_update         | length | release_year | rental_duration | rental_rate | replacement_cost | title | language_id | original_language_id |
|---------|-------------|---------------------|--------|--------------|-----------------|-------------|------------------|-------|-------------|----------------------|
| 1       | B           | 2017-05-05 16:37:44 | 1      | 0            | 0               | 0           | 0                | A     | 1           | 1                    |

As well as other nested entities, including language.

### Development

To deploy a build:
```sh
./gradlew clean build jar testClasses javadoc
```

To make a new release:
 - Make sure that there is a namespace: https://issues.sonatype.org/browse/OSSRH-30923
 1. Grab the name of the GPG_KEY generated from (need this for nexus publication):
 ```sh
gpg --batch --gen-key << EOF
  Key-Type: 1
  Key-Length: 2048
  Subkey-Type: 1
  Subkey-Length: 2048
  Name-Real: Adam Bennett
  Name-Email: adben002@gmail.com
  Passphrase: GPG_PASSWORD
  Expire-Date: 0
EOF
```
 2. Upload the key:
 ```sh
  gpg --keyserver hkp://pool.sks-keyservers.net --send-keys GPG_KEY
```
 3. Update version and README for the new version.
 4. Run
```sh
./gradlew clean build jar testClasses javadoc publish -P snapshotRepoPass='https://issues.sonatype.org_PASSWORD' -P signing.keyId=GPG_KEY -P signing.password=GPG_PASSWORD -P signing.secretKeyRingFile=PATH_TO_GPG/secring.gpg
```
 5. Go to https://oss.sonatype.org/#stagingRepositories
 6. When the package appears, click close.
 7. Wait for the close to complete, then click release.
