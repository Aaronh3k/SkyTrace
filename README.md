# SkyTrace - Real-time Flight Tracking App 🛫 🌍

SkyTrace is a feature-rich Android application designed for aviation enthusiasts and frequent flyers. The app provides real-time flight tracking information, allowing users to view all current flights on a map along with their location, speed, altitude, and status updates. 

## 🎯 Functionality

| Feature 🎁 | Description 📝 | Tool 🛠️ |
| --- | --- | --- |
| Splash Screen | The app welcomes users with a visually appealing splash screen. | - |
| Authentication | Secure login/Sign Up process is implemented via email/password and Google authentication. | Firebase Auth (Email + Google) |
| Data Persistence | User data such as account information and tracked flights are persistently stored and can be viewed or removed as needed. | Firebase (Database + Cloud Storage) |
| CRUD Operations | Users can perform create, read, update, and delete (CRUD) operations on their account entries. They can also add and remove tracked flights, which are displayed as a list. | - |
| Real-Time Mapping | The location of flights is displayed on Google Maps, allowing users to view real-time information about nearby and global flights. | Google Maps |
| Third-Party API | The app integrates with the OpenSky Network API to provide accurate and up-to-date flight information. | OpenSky Network |

## 📐 UML Class Diagram

![UML](https://github.com/Aaronh3k/SkyTrace/assets/24919671/c8d8a831-ad74-467e-9e0a-850c8f9b21fe)

## 🚁 UX Approach

The User Experience approach adopted for the development of SkyTrace prioritized user-friendliness and simplicity. With a keen focus on Material Design guidelines, the design process ensured a blend of visual appeal with practical functionality. The intent was to create an application that is not only aesthetically pleasing but also retains a high degree of usability.

Understanding the needs of both aviation enthusiasts and casual users was key in shaping the design. Clear labeling, intuitive navigation, and recognizable icons were incorporated to offer a seamless experience, enabling users to navigate the app with ease and minimal confusion.

The implementation of real-time flight tracking on a map interface further enhances user interaction, offering a visual and interactive way of understanding flight patterns and information. The integration of third-party APIs like OpenSky Network ensures the accuracy and timeliness of flight data, which is crucial for a trustworthy user experience.

Finally, the app’s functionality was designed to be clear and concise. From secure authentication to persistent data storage, every feature was developed with user convenience and utility in mind. This comprehensive approach to the User Experience aims to make SkyTrace an efficient and enjoyable tool for all users, regardless of their aviation knowledge or app usage expertise.

## 🧰 DX Approach

The SkyTrace app was developed using the Model-View-ViewModel (MVVM) architectural pattern. This approach is a popular architectural pattern in software development that effectively separates the business logic of an application from its presentation logic.

The MVVM pattern simplifies the app's structure by dividing it into three distinct parts: Model, View, and ViewModel. This separation facilitates easier testing and maintenance of the app, as each component can be developed and tested independently.

**Model:** This represents the business logic of the application, which includes the data related to flights and how it is stored.

**View:** This represents the presentation of data to the user, including the User Interface (UI) and any user interaction. The view is responsible for displaying the data received from the ViewModel.

**ViewModel:** The ViewModel serves as an intermediary between the View and the Model. It handles user inputs, processes the data from the Model, and updates the View accordingly.

Adopting the MVVM pattern also made it easier to manage the app's data flow, ensuring that the UI remains consistent with the underlying data. This robust approach to the Developer Experience (DX) aims to make SkyTrace a well-structured and maintainable app.

## 🗂️ Git Approach

The development of the SkyTrace app was organized using a structured Git approach. This approach promoted a well-documented and efficient development process. It allowed for change tracking over time, maintenance of a clean and stable codebase, and facilitated continuous improvement and bug fixes.

The following Git approach was used during the development process:

1. **Branches:** Two main branches were used - the main branch (master) for stable releases and a development branch (dev) for ongoing development work.

2. **Feature Development:** The dev branch was checked out to start working on the assigned issue. All changes and updates were committed to this branch.

3. **Pull Requests (PRs):** Once a feature or bug fix was implemented and tested, a PR was created to merge the changes from the dev branch into the master branch.

4. **Code Review:** The PR was then reviewed. Any necessary changes were made based on feedback before the final merging.

5. **Merging and Issue Resolution:** After a thorough review, the PR was merged into the master branch, and the linked issue was marked as resolved.

6. **Releases:** After a set of features were implemented and merged into the master branch, a tagged release of the application was created.

7. **Bug Fixes:** If any bugs were identified after a release, they were treated as new issues, and the above process was followed to resolve them. After bugs were fixed and tested, a new tagged release of the application was created.

This structured Git approach played a pivotal role in maintaining an organized, efficient, and successful development process for the SkyTrace app.

## 🎙️ Personal Statement

The development of SkyTrace, a real-time flight tracking app, was an engaging and challenging endeavor. As the sole developer on this project, it provided me with an opportunity to delve into Android app development and utilize a variety of tools and technologies.

Being new to mobile app development, I needed to rapidly acquire and apply new skills. Implementing the MVVM design pattern, working with Google Maps API, utilizing Firebase for authentication and storage, and integrating the OpenSky Network API were all part of this steep learning trajectory.

Throughout this project, Git served as a vital tool for version control, assisting me in managing the codebase effectively. I faced numerous issues during the development phase, but the guidance and resources from online communities such as Stack Overflow and Chat GPT proved to be invaluable in overcoming these challenges.

One significant limitation I encountered was with the OpenSky Network API. Due to the limited flight data provided by the free tier of the API, I was unable to implement certain functionalities such as search and flight path tracking. This was a major constraint as it restricted the potential features and functionality of the app.

Furthermore, due to time constraints and the necessity to balance work with other modules, I was unable to fully implement a few features, such as a more detailed flight history and advanced user settings. These limitations, however, have been identified as areas for future enhancement.

In conclusion, the development of SkyTrace has been a rewarding experience. It has enabled me to grow as a developer, gain practical experience in mobile app development, and understand the intricacies of creating a user-oriented application. Despite the challenges and limitations, I am enthusiastic about the potential improvements and refinements that I can introduce to this project in the future.
## 📚 References

The development of the SkyTrace application heavily relied on the following resources:

1. [Firebase Authentication Documentation](https://firebase.google.com/docs/auth)
2. [Google Maps Platform Documentation](https://developers.google.com/maps/documentation)
3. [OpenSky Network API Documentation](https://openskynetwork.github.io/opensky-api/): The OpenSky Network API documentation was crucial in fetching accurate and up-to-date flight information for the app.
4. [ADSB-Exchange Map](https://github.com/amnesica/ADSB-Exchange-Map): A Kotlin flight tracking application which served as a valuable reference during the development process.
5. [Stack Overflow](https://stackoverflow.com/): An invaluable resource for resolving the challenges and issues encountered during the development of SkyTrace.

