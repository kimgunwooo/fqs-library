## 🐥 [FQS](https://github.com/kimgunwooo/FQS-flexible-queue-service) SDK
- FQS를 편리하게 사용하기 위한 SDK 입니다.
- Java-sdk는 Java 17 버전으로 작성되었습니다.
- Spring-sdk는 Java 17, Spring Boot 3.3.4 버전으로 작성되었습니다.
- SDK 자체에서 외부 의존성에 대해 격리하고자 최대한 적은 의존성으로 구현되었습니다.

## 🏃‍♂️‍➡️ 시작하기
- 먼저 FQS 서비스를 통해서 대기열을 생성합니다.
- 생성된 대기열의 이름과, 발급받은 SecretKey를 자신의 로컬에 저장합니다. 절대 외부로 노출되면 안됩니다!

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kimgunwooo:FQS-sdk:${version}'
    ...
}
```
- 다음과 같은 `build.gradle` 설정을 통해 해당 SDK를 사용할 수 있습니다.
- ${verison} 변수에는 자신이 원하는 버전 정보를 입력해주세요!
- Jitpack 기반으로 배포되었기에 `maven { url 'https://jitpack.io' }` 과정이 꼭 필요합니다.

### Java-SDK
- Java 기반의 Framework 중 Spring을 사용해서 예제 코드를 구현했습니다.
  - 다른 Framework는 아직 미숙해서 추가되지 않았습니다..
```java
@Configuration
public class FQSConfig {

    @Value("${f4.fqs.secretKey}")
    private String secretKey;

    @Value("${f4.fqs.queueName}")
    private String queueName;

    /**
     * Creates a bean of type FQSSdk.
     *
     * This method initializes an instance of FQSSdkImpl by using the configuration
     * values (secretKey and queueName) injected from the application properties.
     *
     * The FQSSdkImpl is initialized with a singleton instance of FQSSdkConfig,
     * ensuring that the SDK is properly configured for use throughout the application.
     *
     * @return an instance of FQSSdk configured with the provided secret key and queue name
     */
    @Bean
    public FQSSdk fqsSdk() {
        // Creates an instance of FQSSdkImpl using the FQSSdkConfig with injected properties
        return new FQSSdkImpl(FQSSdkConfig.getInstance(secretKey, queueName));
    }
}
```
- Spring Boot 기준으로 `application.yml` 또는 `application.properties` 파일에서 변수로 주입받은 자신의 대기열 이름과, SecretKey를 변수로 주입받습니다.
  - 다른 Framework 에서도 해당 대기열 이름과, SecretKey가 외부로 노출되지 않도록 주의해주세요.
- 위의 예시처럼 FQSSdk 를 Bean으로 등록하고, 자신의 비즈니스 로직에서 의존성 주입을 통해 쉽게 사용될 수 있도록 설계되었습니다.

### Spring-SDK
- Spring Boot 기반의 SDK도 구현되었습니다.
- 최소한의 의존성으로 구현되었기에 외부에서 받는 영향을 줄였습니다.

> application.yml
```yaml
f4:
  fqs:
    secretKey: ${SECRET_KEY} # Please enter the issued secret key here
    queueName: ${QUEUE_NAME} # Please enter the name of the queue you created
```
> application.properties
```properties
f4.fqs.secretKey=${SECRET_KEY} # Please enter the issued secret key here
f4.fqs.queueName=${QUEUE_NAME} # Please enter the name of the queue you created
```

- 각자의 선호에 맞게 application 파일을 구성하면 됩니다.
- 대신 해당 계층구조를 지켜야 올바른 작동이 가능합니다.

### Example Code
```java
public class FQSSdkExample {

    private final FQSSdk fqsSdk;

    /**
     * Add an item to the queue.
     *
     * @return The identifier of the added item.
     */
    public String addItemToQueue() {
        // TODO: Add your custom logic here (e.g., prepare data to be added)
        return fqsSdk.enqueue(); // Add item to the queue
    }

    /**
     * Consume items from the queue.
     *
     * @param size The number of items to consume.
     * @return A list of consumed items.
     */
    public List<String> consumeItemsFromQueue(Integer size) {
        // TODO: Add your custom logic here (e.g., validate the number of items to consume)
        return fqsSdk.dequeue(size); // Consume items from the queue
    }

    /**
     * Retrieve the rank of a specific item in the queue.
     *
     * @param identifier The unique identifier of the item.
     * @return The rank of the specified item.
     */
    public Long getQueueRank(UUID identifier) {
        // TODO: Add your custom logic here (e.g., check if the item exists)
        return fqsSdk.retrieveQueueRank(identifier); // Retrieve the rank of the specific item
    }
}
```
- 위 처럼 FQSSdk 인터페이스를 주입받아 쉽게 사용할 수 있습니다.
- FQSSdk 전/후로 각자에 맞는 비즈니스 로직을 추가해서 다양하게 사용 가능합니다.

## ❤️ 기여하기
- FQS-sdk에 기여하고 싶으신가요? 다음 단계를 따라주세요 :)

1. 이 리포지토리를 포크하세요.
2. 새로운 브랜치를 생성하세요:
  ```bash
  git checkout -b release/{version} + option
  ```
3. 변경 사항을 커밋하세요:
  ```bash
  git commit -m "Add some feature"
  ```
4. 브랜치에 푸시하세요:
  ```bash
  git push origin release/{version} + option
  ```
5. 풀 리퀘스트를 생성하세요.

- 담당자들이 해당 PR을 확인한 후, 리뷰와 함께 merge 여/부를 결정하게됩니다.

## 📪 연락처
FQS-sdk에 대한 질문이나 피드백이 있으시면 다음의 이메일로 연락해 주세요: kw4u1223@gmail.com

## 📝 라이센스
```
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work
      (an example is provided in the Appendix below).

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based on (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and Derivative Works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control systems,
      and issue tracking systems that are managed by, or on behalf of, the
      Licensor for the purpose of discussing and improving the Work, but
      excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to reproduce, prepare Derivative Works of,
      publicly display, publicly perform, sublicense, and distribute the
      Work and such Derivative Works in Source or Object form.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright statement to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Additional Liability. While redistributing
      the Work or Derivative Works thereof, You may choose to offer,
      and charge a fee for, acceptance of support, warranty, indemnity,
      or other liability obligations and/or rights consistent with this
      License. However, in accepting such obligations, You may act only
      on Your own behalf and on Your sole responsibility, not on behalf
      of any other Contributor, and only if You agree to indemnify,
      defend, and hold each Contributor harmless for any liability
      incurred by, or claims asserted against, such Contributor by reason
      of your accepting any such warranty or additional liability.

   END OF TERMS AND CONDITIONS

   APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in the appropriate
      comment syntax for the file format. We also recommend that a
      file or class name and description of purpose be included on the
      same "printed page" as the copyright notice for easier
      identification within third-party archives.

   Copyright [yyyy] [name of copyright owner]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
